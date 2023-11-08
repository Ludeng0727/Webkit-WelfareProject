package webkit.welfare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webkit.welfare.domain.FamilySituationEnum;
import webkit.welfare.domain.LifeCycleEnum;
import webkit.welfare.domain.UserEntity;
import webkit.welfare.domain.WelfareEntity;
import webkit.welfare.repository.UserRepository;
import webkit.welfare.repository.WelfareRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class WelfareService {
    private final WelfareRepository welfareRepository;
    private final UserRepository userRepository;

    public List<WelfareEntity> getAllWelfareByUserInfo(String userId){
        UserEntity userEntity = userRepository.findById(userId).get();

        LifeCycleEnum lifeCycle = userEntity.getLifeCycle();
        FamilySituationEnum familySituation = userEntity.getFamilySituation();
        String city = userEntity.getCtpvNm();
        String sgg = userEntity.getSggNm();

        int[] indexList = {0,0,0,0,0};

        List<WelfareEntity> sortedWelfareList = new ArrayList<>();
        List<WelfareEntity> welfareList = welfareRepository.findAll();
        welfareList.forEach(welfare->{
            int count = 0;
            if (welfare.getLifeNmArray() != null && welfare.getLifeNmArray().contains(lifeCycle.getHangeul())){
                count++;
            }
            if (welfare.getTrgterIndvdlNmArray() != null && welfare.getTrgterIndvdlNmArray().contains(familySituation.getHangeul())){
                count++;
            }
            if (Objects.equals(city, welfare.getCtpvNm())){
                count++;
            }
            if (Objects.equals(sgg, welfare.getSggNm())){
                count++;
            }

            if (count == 4){
                sortedWelfareList.add(indexList[0], welfare);
                indexList[1]++;
                indexList[2]++;
                indexList[3]++;
                indexList[4]++;
            } else if (count == 3) {
                sortedWelfareList.add(indexList[1], welfare);
                indexList[2]++;
                indexList[3]++;
                indexList[4]++;
            }else if(count == 2){
                sortedWelfareList.add(indexList[2], welfare);
                indexList[3]++;
                indexList[4]++;
            } else if (count ==1) {
                sortedWelfareList.add(indexList[3], welfare);
                indexList[4]++;
            } else {
                sortedWelfareList.add(welfare);
            }

        });

        return sortedWelfareList;

    }

    public List<WelfareEntity> getAllWelfareByKeyword(String keyword){
        return welfareRepository.findAllByServNmContaining(keyword);
    }

    public List<WelfareEntity> getAllWelfare(){
        return welfareRepository.findAll();
    }
}
