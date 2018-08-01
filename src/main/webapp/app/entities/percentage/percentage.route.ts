import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PercentageComponent } from './percentage.component';
import { PercentageDetailComponent } from './percentage-detail.component';
import { PercentagePopupComponent } from './percentage-dialog.component';
import { PercentageDeletePopupComponent } from './percentage-delete-dialog.component';

export const percentageRoute: Routes = [
    {
        path: 'percentage',
        component: PercentageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.percentage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'percentage/:id',
        component: PercentageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.percentage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const percentagePopupRoute: Routes = [
    {
        path: 'percentage-new',
        component: PercentagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.percentage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'percentage/:id/edit',
        component: PercentagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.percentage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'percentage/:id/delete',
        component: PercentageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jeconomizerApp.percentage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
