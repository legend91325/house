package house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Legend91325 on 2019/6/27.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeiKeQuery {
    private Integer page=1;
    //bp
    private String startMoney="0";
    //ep
    private String endMoney="10000";

    //ba
    private String startArea="0";
    //ea
    private String endArea="1000";

    //几室
    private List<String> rooms;

    //房源地理位置
    private String housePostion;

    private String communitPostion;

    //房源特色
    private List<String> houseFeatures;

    //朝向
    private List<String> orientations;

    //楼层
    private List<String> floors;

    //楼龄
    private List<String> houseAges;

    //装修
    public List<String> houseDecoration;

    //用途
    public List<String> houseUsings;

    //电梯
    public List<String> hosueDianTis;

    //供暖
    public List<String> houseGongNuans;

    //房屋权属
    public List<String> houseQuanShus;

    //房屋建筑结构
    public List<String> houseJieGous;
}
