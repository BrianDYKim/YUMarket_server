package team.project.yumarket.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.network.dto.response.TownMarketResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.TownMarketRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TownMarketApiServiceTest {

    @Mock
    private TownMarketRepository townMarketRepository;

    @InjectMocks
    private TownMarketApiService townMarketApiService;

    public MockMvc mockMvc;

    @BeforeEach
    public void prepare() {
        mockMvc = MockMvcBuilders.standaloneSetup(townMarketApiService).build();
    }

    @Test
    @DisplayName("read : ")
    void read() {

    }

    private TownMarket createMarket() {
        return TownMarket.builder()
                .id(1L)
                .name("영남상회")
                .isOpen(true)
                .latitude(128.0)
                .longitude(37.5)
                .marketImageUrl("예시 url")
                .build();
    }

}