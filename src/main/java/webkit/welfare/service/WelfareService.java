package webkit.welfare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.repository.WelfareRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WelfareService {
    private final WelfareRepository welfareRepository;

    public void saveAllWelfareList(List<WelfareEntity> welfareEntityList){
        welfareRepository.saveAll(welfareEntityList);
    }
}
