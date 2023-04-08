package codewifi.sdk.sdkHoroscope;

import codewifi.sdk.sdkHoroscope.response.HoroscopeSdkResponse;

public interface HoroscopeSdkService {
    HoroscopeSdkResponse getHoroscopeInfo(String time, String star);
}
