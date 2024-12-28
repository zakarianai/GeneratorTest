import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SrvrecomendationService {
private apiUrl = 'http://localhost:8888/JAVASCANNER/scanProject/recomendations'; // URL de l'API backend

  constructor(private http: HttpClient) {}

  // Méthode pour envoyer une requête get avec FormData
  recomendation(path: string): Observable<{ [category: string]: string[] }> {
    const url = `${this.apiUrl}?path=${encodeURIComponent(path)}`; // Ajouter le paramètre "path" à l'URL
  
    return this.http.get<{ [category: string]: string[] }>(url); // Attente d'une réponse JSON
  }
}
