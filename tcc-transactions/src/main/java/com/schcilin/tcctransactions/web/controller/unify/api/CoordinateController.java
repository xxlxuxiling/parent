package com.schcilin.tcctransactions.web.controller.unify.api;

import com.schcilin.tcctransactions.anno.Delay;
import com.schcilin.tcctransactions.anno.RandomlyThrowsException;
import com.schcilin.tcctransactions.pojo.TccRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Author: schicilin
 * Date: 2019/4/10 0:40
 * Content:tcc事务协调器
 */
@RestController
@RequestMapping(value = "/api/v1/coordinate",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CoordinateController {

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "确认预留资源", notes = "确认预留资源")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/confirmation")
    public void confirm(@Valid @RequestBody TccRequest request, BindingResult result) {
        //tccService.confirm(request);
    }

    @Delay
    @RandomlyThrowsException
    @ApiOperation(value = "撤销预留资源", notes = "撤销预留资源")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping( "/cancellation")
    public void cancel(@Valid @RequestBody TccRequest request, BindingResult result) {
        //tccService.cancel(request);
    }
}
