package codewifi.service;

import codewifi.repository.model.UserModel;
import codewifi.request.wifi.StarFortuneRequest;
import codewifi.response.wifi.StarResponse;
import org.springframework.stereotype.Service;

@Service
public interface StarService {
    StarResponse getStarContent(StarFortuneRequest request, UserModel userModel);
}
