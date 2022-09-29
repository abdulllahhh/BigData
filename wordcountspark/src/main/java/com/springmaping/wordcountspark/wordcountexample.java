/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springmaping.wordcountspark;

import java.util.Arrays;
import java.util.Iterator;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

/**
 *
 * @author AFAQE3
 */
public class wordcountexample {

    public static void main(String[] args) {
     
        SparkConf conf = new SparkConf().setAppName("wordcount").setMaster("local[1]");
        SparkContext sparkContext = new SparkContext(conf);
       JavaRDD<String> data1=  sparkContext.textFile("E:\\file 1880.txt", 1).toJavaRDD();
        SparkSession session1  =  new SparkSession(sparkContext);
        
      //  SparkSession session = SparkSession.builder().master("local[*]").getOrCreate();
   //     Dataset<String> data = session.read().textFile("E:\\data.txt");
       // data.show();
      //  JavaRDD<String> datardd = data.toJavaRDD();
         data1.flatMap(new FlatMapFunction<String, String>() {

            @Override
            public Iterator<String> call(String t) throws Exception {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String x[] = t.split(" ");
                return Arrays.asList(x).iterator();
            }
        }).mapToPair(new PairFunction<String, String, Integer>() {

            @Override
            public Tuple2<String, Integer> call(String t) throws Exception {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return new Tuple2<>(t, 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {

            @Override
            public Integer call(Integer t1, Integer t2) throws Exception {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return t1 + t2;
            }
        }).saveAsTextFile("E:\\To be deleted\\result15");
    }
}
