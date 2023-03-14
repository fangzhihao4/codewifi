package codewifi.common;

import codewifi.annotation.config.MyTypedJsonJacksonCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedissonService extends Redisson {

	@Resource
	private ObjectMapper objectMapper;

	protected RedissonService(Config buildConfig) {
		super(buildConfig);
	}

	public <V> RTimeSeries<V> getTimeSeries(String name, Class<?> clazz) {
		return super.getTimeSeries(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RStream<K, V> getStream(String name, Class<?> clazz) {
		return super.getStream(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RGeo<V> getGeo(String name, Class<?> clazz) {
		return super.getGeo(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RBucket<V> getBucket(String name, Class<?> clazz) {
		return super.getBucket(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RHyperLogLog<V> getHyperLogLog(String name, Class<?> clazz) {
		return super.getHyperLogLog(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RList<V> getList(String name, Class<?> clazz) {
		return super.getList(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RListMultimap<K, V> getListMultimap(String name, Class<?> clazz) {
		return super.getListMultimap(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(String name, Class<?> clazz,
			LocalCachedMapOptions<K, V> options) {
		return super.getLocalCachedMap(name, new TypedJsonJacksonCodec(clazz, objectMapper), options);
	}

	public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(String name, Class<?> clazz) {
		return getSetMultimapCache(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RListMultimapCache<K, V> getListMultimapCache(String name, Class<?> clazz) {
		return super.getListMultimapCache(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RSetMultimap<K, V> getSetMultimap(String name, Class<?> clazz) {
		return super.getSetMultimap(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RSetCache<V> getSetCache(String name, Class<?> clazz) {
		return super.getSetCache(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RMapCache<K, V> getMapCache(String name, Class<?> clazz) {
		return super.getMapCache(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RMapCache<K, V> getMapCache(String name, Class<?> clazz, MapOptions<K, V> options) {
		return super.getMapCache(name, new TypedJsonJacksonCodec(clazz, objectMapper), options);
	}

	public <K, V> RMap<K, V> getMap(String name, Class<?> clazz) {
		return super.getMap(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <K, V> RMap<K, V> getMap(String name, Class<?> clazz, MapOptions<K, V> options) {
		return super.getMap(name, new TypedJsonJacksonCodec(clazz, objectMapper), options);
	}

	public <K, V> RMap<K, V> getMap(String name, Class<?> keyClass, Class<?> valueClass) {
		return super.getMap(name, new MyTypedJsonJacksonCodec(keyClass, valueClass, objectMapper));
	}

	public <V> RSet<V> getSet(String name, Class<?> clazz) {
		return super.getSet(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public RScheduledExecutorService getExecutorService(String name, Class<?> clazz) {
		return super.getExecutorService(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public RScheduledExecutorService getExecutorService(String name, Class<?> clazz, ExecutorOptions options) {
		return super.getExecutorService(name, new TypedJsonJacksonCodec(clazz, objectMapper), options);
	}

	public RRemoteService getRemoteService(String name, Class<?> clazz) {
		return super.getRemoteService(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RSortedSet<V> getSortedSet(String name, Class<?> clazz) {
		return super.getSortedSet(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RScoredSortedSet<V> getScoredSortedSet(String name, Class<?> clazz) {
		return super.getScoredSortedSet(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public RTopic getTopic(String name, Class<?> clazz) {
		return super.getTopic(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RQueue<V> getQueue(String name, Class<?> clazz) {
		return super.getQueue(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RTransferQueue<V> getTransferQueue(String name, Class<?> clazz) {
		return super.getTransferQueue(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RRingBuffer<V> getRingBuffer(String name, Class<?> clazz) {
		return super.getRingBuffer(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RBlockingQueue<V> getBlockingQueue(String name, Class<?> clazz) {
		return super.getBlockingQueue(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(String name, Class<?> clazz) {
		return super.getBoundedBlockingQueue(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RDeque<V> getDeque(String name, Class<?> clazz) {
		return super.getDeque(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RBlockingDeque<V> getBlockingDeque(String name, Class<?> clazz) {
		return super.getBlockingDeque(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RBloomFilter<V> getBloomFilter(String name, Class<?> clazz) {
		return super.getBloomFilter(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RPriorityQueue<V> getPriorityQueue(String name, Class<?> clazz) {
		return super.getPriorityQueue(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(String name, Class<?> clazz) {
		return super.getPriorityBlockingQueue(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(String name, Class<?> clazz) {
		return super.getPriorityBlockingDeque(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

	public <V> RPriorityDeque<V> getPriorityDeque(String name, Class<?> clazz) {
		return super.getPriorityDeque(name, new TypedJsonJacksonCodec(clazz, objectMapper));
	}

}
