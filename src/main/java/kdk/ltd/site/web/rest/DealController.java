package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.DealDto;
import kdk.ltd.site.root.dto.DealSearchCriteria;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.services.DealService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("deals")
public class DealController {

    private DealService<Deal> dealService;

    @Inject
    public DealController(DealService<Deal> dealService) {
        this.dealService = dealService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public DealDto getOne(@PathVariable Long id) {
        Deal deal = dealService.find(id);
        return DealDto.build(deal);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<DealDto> findPage(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<Deal> deals = dealService.findAll(pageable);
        return new PageImpl<>(transformDealsInDTOs(deals), pageable, deals.getTotalElements());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Deal deal) {
        this.dealService.save(deal);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public void createList(@RequestBody List<Deal> deals) {
        this.dealService.save(deals);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody Deal deal) {
        dealService.update( deal );
    }

    @RequestMapping(method = RequestMethod.GET, params = "search")
    public Page<DealDto> search(
                                 @PageableDefault Pageable pageable,
                                 @RequestBody DealSearchCriteria criteria) {
        Page<Deal> deals = dealService.search(criteria, pageable);
        return new PageImpl<>(transformDealsInDTOs(deals), pageable, deals.getTotalElements());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        dealService.delete(id);
    }

    protected List<DealDto> transformDealsInDTOs(Iterable<Deal> documents) {
        return StreamSupport.stream(documents.spliterator(), false)
                .map(DealDto::build)
                .collect(Collectors.toList());
    }
}
