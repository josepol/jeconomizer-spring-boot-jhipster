import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Movement } from './movement.model';
import { MovementService } from './movement.service';

@Component({
    selector: 'jhi-movement-detail',
    templateUrl: './movement-detail.component.html'
})
export class MovementDetailComponent implements OnInit, OnDestroy {

    movement: Movement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private movementService: MovementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMovements();
    }

    load(id) {
        this.movementService.find(id).subscribe((movement) => {
            this.movement = movement;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMovements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'movementListModification',
            (response) => this.load(this.movement.id)
        );
    }
}
