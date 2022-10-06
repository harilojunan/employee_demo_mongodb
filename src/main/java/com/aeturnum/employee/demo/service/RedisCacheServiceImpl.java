package com.aeturnum.employee.demo.service;
//package com.aeturnum.employeeDemo.service;
//
//import java.util.List;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import com.aeturnum.employeeDemo.entity.Employee;
//import com.google.gson.Gson;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//@Service
//public class RedisCacheServiceImpl implements RedisCacheService {
//
//	@Autowired
//	private JedisPool jedisPool;
//	
//	private final Gson gson = new Gson();
//
//	private final Logger logger = LogManager.getLogger(RedisCacheServiceImpl.class);
//
//	// TTL(Time to live) of session data in seconds
//	@Value("${redis.sessiondata.ttl}")
//	private int sessiondataTTL;
//
//	// Acquire Jedis instance from the jedis pool.
//	private Jedis acquireJedisInstance() {
//
//		return jedisPool.getResource();
//	}
//
//	// Releasing the current Jedis instance once completed the job.
//	private void releaseJedisInstance(Jedis jedis) {
//
//		if (jedis != null) {
//			jedis.close();
//			jedis = null;
//		}
//	}
//
//	@Override
//	public Employee storeEmployee(String id, Employee employee) {
//		// TODO Auto-generated method stub
//		Jedis jedis = null;
//		try {
//			jedis = acquireJedisInstance();
//			String json = gson.toJson(employee);
//			jedis.set(id, json);
//			jedis.expire(id, sessiondataTTL);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("Error occured while storing data to the cache ");
//			releaseJedisInstance(jedis);
//			throw new RuntimeException(e);
//
//		} finally {
//			releaseJedisInstance(jedis);
//		}
//		return employee;
//	}
//
//	@Override
//	public Employee retrieveEmployee(String id) {
//		Jedis jedis = null;
//
//		try {
//
//			jedis = acquireJedisInstance();
//
//			String employeeJson = jedis.get(id);
//
//			if (StringUtils.hasText(employeeJson)) {
//				return gson.fromJson(employeeJson, Employee.class);
//			}
//
//		} catch (Exception e) {
//			logger.error("Error occured while retrieving data from the cache ");
//			releaseJedisInstance(jedis);
//			throw new RuntimeException(e);
//
//		} finally {
//			releaseJedisInstance(jedis);
//		}
//
//		return null;
//	}
//
//	@Override
//	public void flushEmployeeCache(String id) {
//		Jedis jedis = null;
//		try {
//
//			jedis = acquireJedisInstance();
//
//			List<String> keys = jedis.lrange(id, 0, -1);
//			if (!CollectionUtils.isEmpty(keys)) {
//				// add the list key in as well
//				keys.add(id);
//
//				// delete the keys and list
//				jedis.del(keys.toArray(new String[keys.size()]));
//			}
//		} catch (Exception e) {
//			logger.error("Error occured while flushing specific data from the cache ");
//			releaseJedisInstance(jedis);
//			throw new RuntimeException(e);
//
//		} finally {
//			releaseJedisInstance(jedis);
//		}
//
//	}
//
//	@Override
//	public void clearAll() {
//		Jedis jedis = null;
//		try {
//
//			jedis = acquireJedisInstance();
//			jedis.flushAll();
//
//		} catch (Exception e) {
//			logger.error("Error occured while flushing all data from the cache ");
//			releaseJedisInstance(jedis);
//			throw new RuntimeException(e);
//
//		} finally {
//			releaseJedisInstance(jedis);
//		}
//
//	}
//
//}
