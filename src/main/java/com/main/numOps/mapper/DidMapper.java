package com.main.numOps.mapper;

import com.main.numOps.domain.did.enums.DidStatus;
import com.main.numOps.domain.did.DidModel;
import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import org.springframework.stereotype.Component;

@Component
public class DidMapper {

    public DidModel toModelList(ActivateDidRequest didRequest) {
        var did = new DidModel();

        did.setStatus(DidStatus.ACTIVE);

        return did;
    }
}
