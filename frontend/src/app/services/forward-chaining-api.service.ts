import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  ForwardChainingRequest,
  TacticalRecommendation,
} from '../models/tactical-recommendation.model';

@Injectable({ providedIn: 'root' })
export class ForwardChainingApiService {
  private readonly apiUrl = 'http://localhost:8080/api/forward-chaining/recommendation';

  constructor(private readonly http: HttpClient) {}

  recommend(request: ForwardChainingRequest): Observable<TacticalRecommendation> {
    return this.http.post<TacticalRecommendation>(this.apiUrl, request);
  }
}
