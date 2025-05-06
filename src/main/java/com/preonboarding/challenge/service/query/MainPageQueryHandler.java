package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.dto.MainPageDto;

public interface MainPageQueryHandler {
    MainPageDto.MainPage getMainPageContents();
}