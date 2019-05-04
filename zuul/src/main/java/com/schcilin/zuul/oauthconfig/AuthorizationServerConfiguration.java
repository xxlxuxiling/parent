package com.schcilin.zuul.oauthconfig;

import com.schcilin.zuul.exception.CustomerWebResponseExceptionTranslator;
import com.schcilin.zuul.interceptor.IntegrationAuthenticationFilter;
import com.schcilin.zuul.model.User;
import com.schcilin.zuul.service.IntegrationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: schcilin
 * @Date: 2019/5/4 11:27
 * @Version 1.0
 * @des: oauth2认证授权服务 AuthorizationServer
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Value("${self.jwt.sign.key}")
    private String jwtSignKey;
    /**
     * 默认值是36000
     */
    @Value("${self.jwt.token.validity}")
    private Integer validity = 36000;


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //  UsernamePasswordAuthenticationFilter 的验证方法 attemptAuthentication() 会将用户表单提交过来的用户名和密码封装成对象委托类 AuthenticationManager 的验证方法 authenticate() 进行身份验证

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerWebResponseExceptionTranslator customerWebResponseExceptionTranslator;
    /**
     * oauth2过滤器链
     */
    @Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;

    @Autowired
    private IntegrationUserDetailsService integrationUserDetailsService;

    /**
     * 密码加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * jwt形式生成token
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
            /**
             * 重写增强token的方法
             * 自定义返回相应的信息
             *
             */

            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                String userName = authentication.getUserAuthentication().getName();
                // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                User user = (User) authentication.getUserAuthentication().getPrincipal();
                /** 自定义一些token属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("userName", userName);
                additionalInformation.put("roles", user.getAuthorities());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
                return enhancedToken;
            }
        };
        jwtAccessTokenConverter.setSigningKey(jwtSignKey);
        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.
                inMemory()
                .withClient("schcilin")
                .authorizedGrantTypes("authorization_code", "implicit")
                .authorities("ROLE_CLIENT").scopes("read", "write").resourceIds("schcilin")
                .accessTokenValiditySeconds(validity).and().withClient("android")
                .authorizedGrantTypes("client_credentials", "password").authorities("ROLE_TRUSTED_CLIENT")
                .scopes("read", "write").resourceIds("android").accessTokenValiditySeconds(validity)
                .secret("secret");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .exceptionTranslator(customerWebResponseExceptionTranslator)
                .reuseRefreshTokens(true)
                .userDetailsService(integrationUserDetailsService);
    }
}
