import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AmountComponent } from './amount.component';
import { AmountDetailComponent } from './amount-detail.component';
import { AmountPopupComponent } from './amount-dialog.component';
import { AmountDeletePopupComponent } from './amount-delete-dialog.component';

export const amountRoute: Routes = [
    {
        path: 'amount',
        component: AmountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.amount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'amount/:id',
        component: AmountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.amount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amountPopupRoute: Routes = [
    {
        path: 'amount-new',
        component: AmountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.amount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amount/:id/edit',
        component: AmountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.amount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amount/:id/delete',
        component: AmountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.amount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
