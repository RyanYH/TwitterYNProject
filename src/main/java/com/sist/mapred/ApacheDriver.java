package com.sist.mapred;
import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class ApacheDriver {
/*
 *  copyFromLocal local haddop
 *  copyToLocal hadoop local
 *  
 *  => appendFromLocal
 *     appendToLocal
 */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        // TODO Auto-generated method stub
    	try{
        File dir=new File("./output");

        if(dir.exists()){
            File[] files=dir.listFiles();
            for(File f:files){
                f.delete();  // -rf
            }
            dir.delete();  // rm  ==> hadoop -rmr
        }
        // hadoop 설정  core-site.xml
        Configuration conf=new Configuration();
        Job job=new Job(conf,"ApacheCount");
        job.setJarByClass(ApacheDriver.class);
        job.setMapperClass(ApacheMapper.class);
        job.setReducerClass(ApacheReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        
        FileInputFormat.addInputPath(job, new Path("/home/sist/bigdataStudy/TwitterYNProject/app.log"));
        FileOutputFormat.setOutputPath(job, new Path("./output"));
        
        job.waitForCompletion(true); }catch(Exception ex){
        	System.out.println(ex.getMessage());
        }finally{
        	File log = new File("/home/sist/bigdataStudy/TwitterYNProject/app.log");
        	log.delete();
        }
    }

}