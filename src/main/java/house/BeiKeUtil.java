package house;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Legend91325 on 2019/6/27.
 */
@Slf4j
public class BeiKeUtil {


    public void handle(BeiKeQuery beiKeQuery) throws IOException {
        List<String> allHouseUrls = getAllHouseUrls(beiKeQuery);
//        log.info(JSON.toJSONString(allHouseUrls));
        List<BeiKeHouseInfo> beiKeHouseInfos = allHouseUrls.stream().map(houseUrl -> {
            try {
                return parseHouseInfo(houseUrl);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
            return null;
        }).filter(beiKeHouseInfo -> Objects.nonNull(beiKeHouseInfo)).collect(Collectors.toList());

        StringBuffer fileContent = new StringBuffer("一共搜索到房源数量："+beiKeHouseInfos.size()+"\n");
        for(BeiKeHouseInfo beiKeHouseInfo: beiKeHouseInfos){
            fileContent.append(beiKeHouseInfo.toString()).append("\n");
            fileContent.append("\n----------------------------------------------------------\n");
        }

        writFile(beiKeQuery,fileContent.toString());
    }
    public String getQueryUrl(BeiKeQuery beiKeQuery){
        StringBuffer urlBuffer = new StringBuffer("");
        if(Objects.nonNull(beiKeQuery.getPage())&&beiKeQuery.getPage()>1){
            urlBuffer.append("pg").append(beiKeQuery.getPage());
        }
        if(StringUtils.isNotEmpty(beiKeQuery.getHousePostion())){
            urlBuffer.append(BeiKeConstants.postionMap.get(beiKeQuery.getHousePostion())).append("/");
        }


        //房源特色
        appendArgs(beiKeQuery.getHouseFeatures(),urlBuffer);

        //房屋建筑
        appendArgs(beiKeQuery.getHouseJieGous(),urlBuffer);


        //房屋权属
        appendArgs(beiKeQuery.getHouseQuanShus(),urlBuffer);

        //电梯
        appendArgs(beiKeQuery.getHosueDianTis(),urlBuffer);

        //电梯
        appendArgs(beiKeQuery.getHouseGongNuans(),urlBuffer);

        //用途
        appendArgs(beiKeQuery.getHouseUsings(),urlBuffer);

        //装修
        appendArgs(beiKeQuery.getHouseDecoration(),urlBuffer);

        //楼龄
        appendArgs(beiKeQuery.getHouseAges(),urlBuffer);

        //楼层
        appendArgs(beiKeQuery.getFloors(),urlBuffer);

        //朝向
        appendArgs(beiKeQuery.getOrientations(),urlBuffer);

        //面积
        urlBuffer.append("ba").append(beiKeQuery.getStartArea()).append("ea").append(beiKeQuery.getEndArea());

        //几室
        appendArgs(beiKeQuery.getRooms(),urlBuffer);


        //价格
        urlBuffer.append("bp").append(beiKeQuery.getStartMoney()).append("ep").append(beiKeQuery.getEndMoney());

        if(StringUtils.isNotEmpty(beiKeQuery.getCommunitPostion())){
            urlBuffer.append(beiKeQuery.getCommunitPostion());
        }

        urlBuffer.append("/");
        return urlBuffer.toString();
    }


    public StringBuffer appendArgs(List<String> args,StringBuffer urlBuffer){
        if(CollectionUtils.isNotEmpty(args)){
            Collections.sort(args);
            for(String arg: args){
                urlBuffer.append(arg);
            }
        }
        return urlBuffer;
    }

    public List<String> getAllHouseUrls(BeiKeQuery beiKeQuery) throws IOException {
        List<String> allUrls = Lists.newArrayList();
        String queryUrl = "https://bj.ke.com/ershoufang/"+getQueryUrl(beiKeQuery);
        String requestHtml = requestHtml(queryUrl);
        int pages = parsePages(requestHtml);
        List<String> houseUrls = parseHouseUrls(requestHtml);
        if(CollectionUtils.isNotEmpty(houseUrls)){
            allUrls.addAll(houseUrls);
        }
        if(pages>1){
            for(int page=2;page<=pages;page++){
                beiKeQuery.setPage(page);
                queryUrl = getQueryUrl(beiKeQuery);
                requestHtml = requestHtml(queryUrl);
                houseUrls = parseHouseUrls(requestHtml);
                if(CollectionUtils.isNotEmpty(houseUrls)){
                    allUrls.addAll(houseUrls);
                }
            }
        }
        return allUrls;
    }

    public BeiKeHouseInfo parseHouseInfo(String houseUrl) throws IOException {
        BeiKeHouseInfo beiKeHouseInfo  = BeiKeHouseInfo.builder().build();
        beiKeHouseInfo.setHouseUrl(houseUrl);
        String requestHtml = requestHtml(houseUrl);
        Document document = Jsoup.parse(requestHtml);
        Element overview = document.select("div.sellDetailPage")
                .select("div[data-component=overviewIntro]")
                .select("div.overview")
                .select("div.content")
                .first();
        //price
        Element price = overview.select("div.price").first();
        String totalPrice = price.select("span.total").first().text();
        beiKeHouseInfo.setTotalPrice(totalPrice);
        String unitPrice = price.select("div.text").select("div.unitPrice").select("span.unitPriceValue").first().text();
        beiKeHouseInfo.setUnitPrice(unitPrice);
        //houseinfo
        Element houseInfo = overview.select("div.houseInfo").first();
        String roomMainInfo = houseInfo.select("div.room").select("div.mainInfo").first().text();
        beiKeHouseInfo.setRoomMainInfo(roomMainInfo);
        String roomSubInfo = houseInfo.select("div.room").select("div.subInfo").first().text();
        beiKeHouseInfo.setRoomSubInfo(roomSubInfo);
        //type
        Element type = overview.select("div.type").first();
        String typeMainInfo = type.select("div.type").select("div.mainInfo").first().text();
        beiKeHouseInfo.setTypeMainInfo(typeMainInfo);
        String typeSubInfo = type.select("div.type").select("div.subInfo").first().text();
        beiKeHouseInfo.setTypeSubInfo(typeSubInfo);

        //area
        Element area = overview.select("div.area").first();
        String areaMainInfo = area.select("div.area").select("div.mainInfo").first().text();
        beiKeHouseInfo.setAreaMainInfo(areaMainInfo);
        String areaSubInfo = area.select("div.area").select("div.subInfo").first().text();
        beiKeHouseInfo.setAreaSubInfo(areaSubInfo);

        //aroundinfo
        Element aroundInfo = overview.select("div.aroundInfo").first();
        String communityName = aroundInfo.select("div.communityName").select("a").first().text();
        beiKeHouseInfo.setCommunityName(communityName);
        Elements areas = aroundInfo.select("div.areaName").select("span.info").first().select("a");
        StringBuffer areaName = new StringBuffer("");
        for(Element areaE: areas){
            areaName.append(areaE.text()).append("  ");
        }
        beiKeHouseInfo.setAreaName(areaName.toString());


        Element mContent = document.select("div.sellDetailPage")
                .select("div.m-content")
                .select("div.box-l")
                .first();
        Element introContent = mContent.select("div[data-component=baseinfo]")
                .select("div")
                .select("div.introContent")
                .first();
        Elements baseLis = introContent.select("div.base").select("div.content").select("ul").select("li");
        StringBuffer introContentBase = new StringBuffer("");
        for(Element baseLi: baseLis){
            String key = baseLi.select("span").first().text();
            String value = baseLi.text();
            introContentBase.append(value).append("\n");
        }
        beiKeHouseInfo.setIntroContentBase(introContentBase.toString());


        Elements transactionLis = introContent.select("div.transaction").select("div.content").select("ul").select("li");
        StringBuffer introContentTransaction = new StringBuffer("");
        for(Element transactionLi: transactionLis){
            String key = transactionLi.select("span").first().text();
            String value = transactionLi.text();
            introContentTransaction.append(value).append("\n");
        }
        beiKeHouseInfo.setIntroContentTransaction(introContentTransaction.toString());


        StringBuffer introContentBaseMore = new StringBuffer("");
        Elements introFeatures = mContent.select("div[data-component=feature]")
                .select("div")
                .select("div");
        for(Element introFeatrue: introFeatures){
            if(introFeatrue.classNames().contains("baseattribute")){
                String name = introFeatrue.select("div.name").text();
                String content = introFeatrue.select("div.content").text();
                introContentBaseMore.append(name).append(":").append(content).append("\n");
            }
        }
        beiKeHouseInfo.setIntroContentBaseMore(introContentBaseMore.toString());
        return beiKeHouseInfo;
    }
    public String requestHtml(String queryUrl) throws IOException {
        OkHttpClient client = OkHttpUtil.getClient();
        Request request = new Request.Builder().url(queryUrl).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String htmlContent = response.body().string();
        return htmlContent;
    }

    public List<String> parseHouseUrls(String htmlContent){
        List<String> urls = Lists.newArrayList();
        Document document = Jsoup.parse(htmlContent);
        Element ulElement = document.select("ul.sellListContent").first();
        if(Objects.isNull(ulElement)){
            log.warn("query no house data");
            return urls;
        }
        Elements liElements = ulElement.select("li.clear");
        for(Element liElement: liElements){
            String url = liElement.select("a").first().attr("href");
            urls.add(url);
        }
        return urls;
    }

    public int parsePages(String htmlContent){
        Document document = Jsoup.parse(htmlContent);
        Element pageElement = document.select("div[comp-module=page]").first();
        if(pageElement==null){
            return 0;
        }
        String pageJson = pageElement.attr("page-data");
        int totalPage = JSON.parseObject(pageJson).getIntValue("totalPage");
        return totalPage;
    }

    public void writFile(BeiKeQuery beiKeQuery,String content) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd_HHmmss");
        String format = simpleDateFormat.format(new Date());
        String fileName = format+".txt";
        File fileResource = new File("src/main/resources/");

        File file = new File(fileResource.getAbsolutePath()+"/"+fileName);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }
}
