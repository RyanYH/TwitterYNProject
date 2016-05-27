package com.sist.twitter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.hadoop.hdfs.server.namenode.status_jsp;
import org.jsoup.safety.Cleaner;

import twitter4j.FilterQuery;
import twitter4j.Logger;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/*
 * 1. Mapper(단어분석) => 형태소 분석
 * 2. Reducer -> 분석결과누적
 * 3. Driver(실행파일)
 * ====================================
 * 분석
 *   = 단순분석(구조가 있는 경우)
 *   = 복합분석(정규식)
 */
public class TwitterMain {	
	
	public static void main(String[] args) throws TwitterException
	{
		Logger logger = Logger.getLogger(TwitterMain.class);
       
		Twitter twitter = TwitterFactory.getSingleton();
		String keyword="곡성";
		Query query = new Query(keyword);
		query.setCount(10);
		twitter4j.QueryResult result = twitter.search(query);
		File file = new File("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/desc.txt");
		if(file.exists())
			file.delete();
		//FileWriter fw=new FileWriter("/home/sist/bigdataStudy/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/desc.txt",true);
		
		for(Status status:result.getTweets())
		{
			if(result.hasNext()){
				  twitter4j.QueryResult nextResult = twitter.search(result.nextQuery());
				  //System.out.println(nextResult);
				  System.out.println("@"+status.getText()+":"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(status.getCreatedAt()));
				//fw.write(status.getText());
				//fw.append("\n");
				  
				  logger.info(status.getText());
			}
		}
		//fw.close();
	}
}
