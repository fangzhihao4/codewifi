package codewifi.service;

import org.springframework.stereotype.Service;

@Service
public interface JobLinkWifiService {

    void pollLinkWifi(String message);
}
