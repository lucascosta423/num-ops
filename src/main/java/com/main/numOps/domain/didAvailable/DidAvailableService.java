package com.main.numOps.domain.didAvailable;

import com.main.numOps.domain.did.enums.DidStatus;
import com.main.numOps.domain.didAvailable.dtos.DidAvailable;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableFilter;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeFilterDTO;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeUpdateRequest;
import com.main.numOps.exeptions.BusinessException;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.Files.NumberFilesService;
import com.main.numOps.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DidAvailableService {
    private final DidAvailableRepository didAvailableRepository;
    private final NumberFilesService numberFilesService;

    public DidAvailableService(DidAvailableRepository didAvailableRepository, NumberFilesService numberFilesService) {
        this.didAvailableRepository = didAvailableRepository;
        this.numberFilesService = numberFilesService;
    }

    public Page<DidAvailableModel> findAll(Pageable pageable) {
        return didAvailableRepository.findAll(pageable);
    }

    public DidAvailableModel findById(Long id) {
        return didAvailableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Number not found"));
    }

    public Page<DidAvailable> findAllAvailable(DidAvailableFilter filter, Pageable pageable) {
        var dids = didAvailableRepository.findWithFilters(
                        DidStatus.AVAILABLE,
                        filter.uf(),
                        filter.area(),
                        pageable)
                .map(DidAvailable::fromEntity);

        if (dids.getTotalElements() == 0) {
            throw new NotFoundException("No DID available for the region.");
        }

        return dids;
    }

    @Transactional
    public void updateStatus(List<Long> ids, DidStatus status) {
        Integer updated = didAvailableRepository.updateStatusDidAvailable(
                ids,
                status,
                DateUtils.nowWithoutNanos()
        );

        if (updated == 0) {
            throw new NotFoundException("None found to update.");
        }
    }

    public Page<DidAvailableModel> listByRange(DidAvailableRangeFilterDTO filterDTO, Pageable pageable) {

        if (Integer.parseInt(filterDTO.end()) < Integer.parseInt(filterDTO.start())) {
            throw new BusinessException("End cannot be less than Start.");
        }

        Page<DidAvailableModel> listed = didAvailableRepository.listByRange(
                filterDTO.cn(),
                filterDTO.prefixo(),
                filterDTO.start(),
                filterDTO.end(),
                pageable
        );

        if (listed.getTotalElements() == 0) {
            throw new NotFoundException("Not a single 'did' found for the chosen range.");
        }

        return listed;
    }

    @Transactional
    public int updateByRange(DidAvailableRangeUpdateRequest request) {
        int updated = didAvailableRepository.updateByRange(
                request.cn(),
                request.prefixo(),
                request.start(),
                request.end(),
                request.status(),
                DateUtils.nowWithoutNanos()
        );

        if (updated == 0) {
            throw new NotFoundException("None found to update.");
        }

        return updated;
    }

    public Void uploadFile(MultipartFile file) {

        numberFilesService.processFile(file);

        return null;
    }

}
