package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.dtos.user.EmailDto;
import com.docsarea.dtos.user.GetDto;
import com.docsarea.dtos.user.UsernameEmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    @Autowired
    AuthFeignController authFeignController ;

    public GetDto update(EmailDto dto){
        return authFeignController.doUpdate(dto);
    }

}
