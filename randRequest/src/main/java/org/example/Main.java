package org.example;

import com.google.api.gax.rpc.NotFoundException;
import com.google.api.gax.rpc.ServerStream;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main implements BackgroundFunction<Main.PubSubMessage> {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static String projectId = "electro-recipe-app";
    private static String instanseId = "bigt-instan-1";
    private static String tableId = "my-bigtable";
    private static String col_family = "my-family";
    private static int row_key = 1;
    private static String req_name = "HTTPName";
    private static String status_line = "Status";
    private static String info = "Info";
    private static String uri = "http://localhost:8080/";
    private static String[] entitys = new String[]{"medicines/", "patients/", "recepts/"};
    private static String[] endpoints = new String[]{"get-all","get-one/"};
    private static int[] nums = new int[]{1,2};
    private static String entity = null;
    private static String endpoint = null;
    private static String num = null;
    private static String url = null;

    public static void main(String[] args) throws IOException {

        BigtableDataClient dataClient = BigtableDataClient.create(projectId, instanseId);

        logger.info("------- HTTP request -------");
        String get_url = urlGenerating();
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        GetMethod get = new GetMethod(get_url);
        try {
            logger.info("--------------- START INFO ---------------\n");
            client.executeMethod(get);
            logger.info("Get status line = " + get.getStatusLine());
            logger.info("Get name = " + get.getName());
            logger.info("Get body String = \n" + get.getResponseBodyAsString());
            logger.info("--------------- END INFO ---------------\n");

            // Write in Table
            writeRowses(dataClient, get);

            // Read table
            readRowses(dataClient);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            System.err.println("Failed to write to non-existent table: " + e.getMessage());
        } finally {
            get.releaseConnection();
            dataClient.close();
        }
    }

    public static void writeRowses(BigtableDataClient dataClient, GetMethod get) throws IOException {
        logger.info("----- Writing to the Table -----");
        String greeting = get.getResponseBodyAsString();
        String status = get.getStatusLine().toString();
        String name = get.getName();
        RowMutation rowMutation =
                RowMutation.create(tableId,String.valueOf(row_key))
                        .setCell(col_family, status_line, status)
                        .setCell(col_family, req_name, name)
                        .setCell(col_family, info, greeting);
        row_key = row_key + 1;
        dataClient.mutateRow(rowMutation);
        logger.info("----- Writing successfully -----");
    }

    public static List<Row> readRowses(BigtableDataClient dataClient){
        logger.info("----- Reading to the Table -----");
        Query query = Query.create(tableId);
        ServerStream<Row> rowStream = dataClient.readRows(query);
        List<Row> tableRows = new ArrayList<>();
        for(Row r : rowStream){
            logger.info("Row Key: " + r.getKey().toStringUtf8());
            tableRows.add(r);
            for(RowCell cell : r.getCells()){
                logger.info("Family: " + cell.getFamily() + "\n" +
                        "Qualifier: " + cell.getQualifier().toStringUtf8() + "\n" +
                        "Value: " + cell.getValue().toStringUtf8() + "\n");
            }
        }
        return tableRows;
    }

    public static int randInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    public static String urlGenerating(){
        int rand_entity = randInt(0,2);
        entity = entitys[rand_entity];

        int rand_endpoint = randInt(0,1);

        if(rand_endpoint == 0){
            endpoint = endpoints[rand_endpoint];
        }
        else{
            int rand_num = randInt(0,1);
            num = String.valueOf(nums[rand_num]);
            endpoint = endpoints[rand_endpoint] + num;
        }

        url = uri + entity + endpoint;
        logger.info("full url = " + url + "\n");

        return url;
    }

    public static class PubSubMessage{
        String message;
    }

    @Override
    public void accept(PubSubMessage data, Context context){
        String msg = data.message != null
                ? new String(Base64.getDecoder().decode(data.message))
                : "Hello World";
        logger.info("MSG = " + msg);
    }

}