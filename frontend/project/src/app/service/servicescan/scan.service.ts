import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScanService {
  private apiUrl = 'http://localhost:8888/JAVASCANNER/scanProject/tests'; // URL de l'API backend

  constructor(private http: HttpClient) {}

  // Méthode pour envoyer une requête GET avec un paramètre dans l'URL
  scanProject(path: string): Observable<string> {
    const url = `${this.apiUrl}?path=${encodeURIComponent(path)}`; // Ajouter le paramètre "path" à l'URL

    return this.http.get<string>(url, {
      responseType: 'text' as 'json' // Précision pour recevoir une réponse en texte brut
    });
  }
}
