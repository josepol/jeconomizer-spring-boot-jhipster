import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Amount } from './amount.model';
import { AmountService } from './amount.service';

@Component({
    selector: 'jhi-amount-detail',
    templateUrl: './amount-detail.component.html'
})
export class AmountDetailComponent implements OnInit, OnDestroy {

    amount: Amount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private amountService: AmountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAmounts();
    }

    load(id) {
        this.amountService.find(id).subscribe((amount) => {
            this.amount = amount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAmounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'amountListModification',
            (response) => this.load(this.amount.id)
        );
    }
}
