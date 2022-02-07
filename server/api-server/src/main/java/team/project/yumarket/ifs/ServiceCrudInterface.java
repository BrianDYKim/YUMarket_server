package team.project.yumarket.ifs;

import team.project.yumarket.network.formats.CommunicationFormat;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
public interface ServiceCrudInterface<T, Req, Res> extends CrudInterface<Req, Res> {

    // Data를 response dto로 변환시킨 이후에 CommunicationFormat으로 감싸서 리턴해주는 메소드
    public CommunicationFormat<Res> response(String url, T data);

    public Res responseData(T data);

    public T requestToEntity(Req request);
}
