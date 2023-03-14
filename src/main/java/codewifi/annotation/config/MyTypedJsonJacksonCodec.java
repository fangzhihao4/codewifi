package codewifi.annotation.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.handler.State;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.JsonJacksonCodec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MyTypedJsonJacksonCodec extends JsonJacksonCodec {

	private final Encoder encoder = new Encoder() {
		@Override
		public ByteBuf encode(Object in) throws IOException {
			ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
			try {
				ByteBufOutputStream os = new ByteBufOutputStream(out);
				if (in instanceof String) {
					String str = (String) in;
					os.write(str.getBytes(StandardCharsets.UTF_8));
				}
				else {
					mapObjectMapper.writeValue((OutputStream) os, in);
				}
				return os.buffer();
			}
			catch (IOException e) {
				out.release();
				throw e;
			}
		}
	};

	private Decoder<Object> createDecoder(final Class<?> valueClass, final TypeReference<?> valueTypeReference) {
		return new Decoder<Object>() {
			@Override
			public Object decode(ByteBuf buf, State state) throws IOException {
				if (valueClass != null) {
					return mapObjectMapper.readValue((InputStream) new ByteBufInputStream(buf), valueClass);
				}
				if (valueTypeReference != null) {
					return mapObjectMapper.readValue((InputStream) new ByteBufInputStream(buf), valueTypeReference);
				}
				return mapObjectMapper.readValue((InputStream) new ByteBufInputStream(buf), Object.class);
			}
		};
	}

	private final Decoder<Object> valueDecoder;

	private final Decoder<Object> mapValueDecoder;

	private final Decoder<Object> mapKeyDecoder;

	private final TypeReference<?> valueTypeReference;

	private final TypeReference<?> mapKeyTypeReference;

	private final TypeReference<?> mapValueTypeReference;

	private final Class<?> valueClass;

	private final Class<?> mapKeyClass;

	private final Class<?> mapValueClass;

	public MyTypedJsonJacksonCodec(Class<?> valueClass) {
		this(valueClass, new ObjectMapper());
	}

	public MyTypedJsonJacksonCodec(Class<?> valueClass, ObjectMapper mapper) {
		this(valueClass, null, null, mapper);
	}

	public MyTypedJsonJacksonCodec(Class<?> mapKeyClass, Class<?> mapValueClass) {
		this(null, mapKeyClass, mapValueClass, new ObjectMapper());
	}

	public MyTypedJsonJacksonCodec(Class<?> mapKeyClass, Class<?> mapValueClass, ObjectMapper mapper) {
		this(null, mapKeyClass, mapValueClass, mapper);
	}

	public MyTypedJsonJacksonCodec(Class<?> valueClass, Class<?> mapKeyClass, Class<?> mapValueClass) {
		this(null, null, null, valueClass, mapKeyClass, mapValueClass, new ObjectMapper());
	}

	public MyTypedJsonJacksonCodec(Class<?> valueClass, Class<?> mapKeyClass, Class<?> mapValueClass,
                                   ObjectMapper mapper) {
		this(null, null, null, valueClass, mapKeyClass, mapValueClass, mapper);
	}

	public MyTypedJsonJacksonCodec(TypeReference<?> valueTypeReference) {
		this(valueTypeReference, new ObjectMapper());
	}

	public MyTypedJsonJacksonCodec(TypeReference<?> valueTypeReference, ObjectMapper mapper) {
		this(valueTypeReference, null, null, mapper);
	}

	public MyTypedJsonJacksonCodec(TypeReference<?> mapKeyTypeReference, TypeReference<?> mapValueTypeReference) {
		this(null, mapKeyTypeReference, mapValueTypeReference);
	}

	public MyTypedJsonJacksonCodec(TypeReference<?> mapKeyTypeReference, TypeReference<?> mapValueTypeReference,
                                   ObjectMapper mapper) {
		this(null, mapKeyTypeReference, mapValueTypeReference, mapper);
	}

	public MyTypedJsonJacksonCodec(TypeReference<?> valueTypeReference, TypeReference<?> mapKeyTypeReference,
                                   TypeReference<?> mapValueTypeReference) {
		this(valueTypeReference, mapKeyTypeReference, mapValueTypeReference, null, null, null, new ObjectMapper());
	}

	public MyTypedJsonJacksonCodec(TypeReference<?> valueTypeReference, TypeReference<?> mapKeyTypeReference,
                                   TypeReference<?> mapValueTypeReference, ObjectMapper mapper) {
		this(valueTypeReference, mapKeyTypeReference, mapValueTypeReference, null, null, null, mapper);
	}

	public MyTypedJsonJacksonCodec(ClassLoader classLoader, MyTypedJsonJacksonCodec codec) {
		this(codec.valueTypeReference, codec.mapKeyTypeReference, codec.mapValueTypeReference, codec.valueClass,
				codec.mapKeyClass, codec.mapValueClass, createObjectMapper(classLoader, codec.mapObjectMapper.copy()));
	}

	MyTypedJsonJacksonCodec(TypeReference<?> valueTypeReference, TypeReference<?> mapKeyTypeReference,
                            TypeReference<?> mapValueTypeReference, Class<?> valueClass, Class<?> mapKeyClass, Class<?> mapValueClass,
                            ObjectMapper mapper) {
		super(mapper);
		this.mapValueDecoder = createDecoder(mapValueClass, mapValueTypeReference);
		this.mapKeyDecoder = createDecoder(mapKeyClass, mapKeyTypeReference);
		this.valueDecoder = createDecoder(valueClass, valueTypeReference);

		this.mapValueClass = mapValueClass;
		this.mapValueTypeReference = mapValueTypeReference;
		this.mapKeyClass = mapKeyClass;
		this.mapKeyTypeReference = mapKeyTypeReference;
		this.valueClass = valueClass;
		this.valueTypeReference = valueTypeReference;
	}

	@Override
	protected void initTypeInclusion(ObjectMapper mapObjectMapper) {
		// avoid type inclusion
	}

	@Override
	public Decoder<Object> getValueDecoder() {
		return valueDecoder;
	}

	@Override
	public Encoder getValueEncoder() {
		return encoder;
	}

	@Override
	public Decoder<Object> getMapKeyDecoder() {
		return mapKeyDecoder;
	}

	@Override
	public Encoder getMapValueEncoder() {
		return encoder;
	}

	@Override
	public Encoder getMapKeyEncoder() {
		return encoder;
	}

	@Override
	public Decoder<Object> getMapValueDecoder() {
		return mapValueDecoder;
	}

}
