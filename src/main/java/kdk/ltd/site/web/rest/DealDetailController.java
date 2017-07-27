package kdk.ltd.site.web.rest;

import kdk.ltd.site.root.dto.DealDTO;
import kdk.ltd.site.root.entities.Deal;
import kdk.ltd.site.root.entities.DealDetail;
import kdk.ltd.site.root.repositories.DealDetailRepository;
import kdk.ltd.site.root.repositories.DealRepository;
import kdk.ltd.site.root.services.DealService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/deals/{dealId}/details", produces = "application/json")
public class DealDetailController {


    private DealDetailRepository detailRepository;

    private DealRepository dealRepository;

    @Inject
    public DealDetailController(DealDetailRepository detailRepository,
                                DealRepository dealRepository) {
        this.detailRepository = detailRepository;
        this.dealRepository = dealRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create( @PathVariable Long dealId,
                            @RequestBody DealDetail dealDetail ) {
        Deal deal = dealRepository.getOne( dealId );
        dealDetail.setDeal(deal);

        detailRepository.save(dealDetail);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{detailId}", method = RequestMethod.PUT)
    public void update(@PathVariable Long detailId,
                            @RequestBody DealDetail detail) {

    }

}
