import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Percentage } from './percentage.model';
import { PercentagePopupService } from './percentage-popup.service';
import { PercentageService } from './percentage.service';
import { Category, CategoryService } from '../category';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-percentage-dialog',
    templateUrl: './percentage-dialog.component.html'
})
export class PercentageDialogComponent implements OnInit {

    percentage: Percentage;
    isSaving: boolean;

    categoryids: Category[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private percentageService: PercentageService,
        private categoryService: CategoryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.categoryService
            .query({filter: 'percentage-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.percentage.categoryId || !this.percentage.categoryId.id) {
                    this.categoryids = res.json;
                } else {
                    this.categoryService
                        .find(this.percentage.categoryId.id)
                        .subscribe((subRes: Category) => {
                            this.categoryids = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.percentage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.percentageService.update(this.percentage));
        } else {
            this.subscribeToSaveResponse(
                this.percentageService.create(this.percentage));
        }
    }

    private subscribeToSaveResponse(result: Observable<Percentage>) {
        result.subscribe((res: Percentage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Percentage) {
        this.eventManager.broadcast({ name: 'percentageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-percentage-popup',
    template: ''
})
export class PercentagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private percentagePopupService: PercentagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.percentagePopupService
                    .open(PercentageDialogComponent as Component, params['id']);
            } else {
                this.percentagePopupService
                    .open(PercentageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
