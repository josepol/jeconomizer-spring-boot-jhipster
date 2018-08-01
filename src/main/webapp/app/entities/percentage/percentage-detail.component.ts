import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Percentage } from './percentage.model';
import { PercentageService } from './percentage.service';

@Component({
    selector: 'jhi-percentage-detail',
    templateUrl: './percentage-detail.component.html'
})
export class PercentageDetailComponent implements OnInit, OnDestroy {

    percentage: Percentage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private percentageService: PercentageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPercentages();
    }

    load(id) {
        this.percentageService.find(id).subscribe((percentage) => {
            this.percentage = percentage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPercentages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'percentageListModification',
            (response) => this.load(this.percentage.id)
        );
    }
}
