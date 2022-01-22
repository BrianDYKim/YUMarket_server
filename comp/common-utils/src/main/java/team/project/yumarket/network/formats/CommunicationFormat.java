package team.project.yumarket.network.formats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunicationFormat<T> {

    private LocalDateTime transactionTime;

    // 요청한 api의 해당 주소
    private String url;

    // data 정보
    private T data;

    private String description;

    private Pagination pagination;

    // Body의 정보가 존재하지 않는 OK method
    public static CommunicationFormat OK(String url) {
        return CommunicationFormat.builder()
                .url(url)
                .transactionTime(LocalDateTime.now())
                .build();
    }

    // Data의 정보를 포함하는 OK method
    public static <T> CommunicationFormat<T> OK(String url, T data) {
        return (CommunicationFormat<T>) CommunicationFormat.builder()
                .url(url)
                .transactionTime(LocalDateTime.now())
                .data(data)
                .build();
    }

    /**
     * paging 정보를 포함하여 정보를 리턴하는 메소드
     * @param url client가 요청한 api 주소
     * @param data client에게 보내지는 data
     * @param pagination client에게 보내지는 data의 페이징 정보
     * @return CommunicationFormat with data and pagination
     */
    public static <T> CommunicationFormat<T> OK(String url, T data, Pagination pagination) {
        return (CommunicationFormat<T>) CommunicationFormat.builder()
                .url(url)
                .transactionTime(LocalDateTime.now())
                .data(data)
                .pagination(pagination)
                .build();
    }

    /**
     * 통신이 실패하였을 때 client에게 보여지는 method
     * @param url client가 요청한 api 주소
     * @param description client의 요청이 실패한 이유에 대해 설명하는 description
     * @return CommunicationFormat with url, and description including why the client's request is failed.
     */
    public static CommunicationFormat ERROR(String url, String description) {
        return CommunicationFormat.builder()
                .url(url)
                .transactionTime(LocalDateTime.now())
                .description(description)
                .build();
    }
}
