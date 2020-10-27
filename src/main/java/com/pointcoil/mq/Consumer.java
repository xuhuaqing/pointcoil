package com.pointcoil.mq;

import com.pointcoil.conf.PointCoilProperties;
import com.pointcoil.dto.map.ThermodynamicDTO;
import com.pointcoil.mapper.ExcelUploadMapper;
import com.pointcoil.mapper.OfficialWebsiteServiceMapper;
import com.pointcoil.mapper.ThermodynamicMapper;
import com.pointcoil.mongo.MongoDbDao;
import com.pointcoil.util.ExcelEventParser;
import com.pointcoil.util.ExcelReader;
import com.pointcoil.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import javax.jms.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static com.pointcoil.util.ExcelEventParser.removeThredList;

/**
 * Created by WuShuang on 2020/4/10.
 */
@Component //注入到Spring容器里面去
public class Consumer {
    @Autowired
    private OfficialWebsiteServiceMapper officialWebsiteServiceMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MongoDbDao mongoDbDao;

    @Autowired
    private ExcelUploadMapper excelUploadMapper;

    @Autowired
    private PointCoilProperties pointCoilProperties;

    @Autowired
    private ExcelEventParser excelEventParser;
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
    private ExecutorService taskExe = new ThreadPoolExecutor(10,20,200L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);


    @JmsListener(destination = "ActiveMQ.DLQ")
    public void DLQ(Message message,Session session) throws JMSException {
        if(message instanceof TextMessage){
            TextMessage textMessage =
                    (TextMessage) message;
            try {
                String text = textMessage.getText();
                String[] split = text.split(",");
                String cityName = split[1];
                List<ThermodynamicDTO> thermodynamicDTOS =
                        ExcelReader.readExcel(split[0]);
                int insertLength = thermodynamicDTOS.size();
                addData(insertLength,thermodynamicDTOS,cityName);
                session.commit();
            }catch (JMSException e){
                e.printStackTrace();
                try {
                    session.rollback();
                }catch (JMSException e1){
                    e.printStackTrace();
                }
            }
        }
    }

    @Autowired
    private ThermodynamicMapper thermodynamicMapper;


    @JmsListener(destination = "queue_SplitExcelPath")
    public void sendMail1(Message message,Session session) throws Exception {
        if(message instanceof TextMessage){
            TextMessage textMessage =
                    (TextMessage) message;
            String text = null;
            try {
                text = textMessage.getText();
                System.err.println(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            String[] split = text.split(",");
            String cityName = split[1];
            List<ThermodynamicDTO> thermodynamicDTOS = null;
            try {
                excelEventParser.processFirstSheet(split[0]);
                thermodynamicDTOS = ExcelEventParser.getThermodynamicDTO();
            } catch (Exception e) {
                e.printStackTrace();
            }
            thermodynamicDTOS.remove(0);
  /*                  List<ThermodynamicDTO> thermodynamicDTOS =
                            ExcelReader.readExcel(split[0]);*/
            int insertLength = thermodynamicDTOS.size();
            addData(insertLength,thermodynamicDTOS,cityName);
            thermodynamicMapper.upd();
            ExcelEventParser.removeThredList();
         /*   taskExe.execute(
                    () ->{
                    }
            );
            try {
                taskExe.execute(
                        () ->{

                });
            }catch (Exception e){
                e.printStackTrace();
                try {
                    session.rollback();
                }catch (JMSException e1){
                    e.printStackTrace();
                }
            }*/
        }

    }

    public String file(MultipartFile file){
        String headName = file.getOriginalFilename();
        String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
        String headNewName = UUID.randomUUID() + headLastName;
        String path = pointCoilProperties.getImageUploadUrl();

        File file3 = new File(path + File.separator + headNewName);

        try {
            file.transferTo(file3);
            return file3.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }




   // @JmsListener(destination = "queue_SplitExcelPath222223")
    public void sendMail(Message message,Session session) throws Exception {

          }


    private void addData(int insertLength,List<ThermodynamicDTO> thermodynamicDTOS,String cityName){
        /* *
         * 首页数据
         **/
        officialWebsiteServiceMapper.addBrand("4a2ed265fae911e9a2500242ac110003",insertLength);
        String homePageInit = pointCoilProperties.getRedis().getHomePageInit();
        redisUtil.remove(homePageInit);
        int i = 0;
        while (insertLength > 3800){
            List<ThermodynamicDTO> thermodynamicDTOS1 = thermodynamicDTOS.subList(i, i + 3800);
            excelUploadMapper.uploadCityThermodynamic(thermodynamicDTOS1,cityName);
            mongoDbDao.insert4(thermodynamicDTOS1,"tbl_industry",cityName);
            i = i + 3800;
            insertLength = insertLength - 3800;
        }
        if (insertLength > 0) {
            List<ThermodynamicDTO> thermodynamicDTOS1 = thermodynamicDTOS.subList(i, i + insertLength);
            excelUploadMapper.uploadCityThermodynamic(thermodynamicDTOS1,cityName);
            mongoDbDao.insert4(thermodynamicDTOS1,"tbl_industry", cityName);
        }
    }

}
