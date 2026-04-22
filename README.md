# Erasmus Magazine Android (MVC structuur-opzet)

Dit is een **eerste structurele opzet** van een Android nieuwsapp voor Erasmus Magazine, met focus op MVC en uitbreidbaarheid.

## Architectuurkeuze

- **UI/Views:** `view/` (Activity + Adapter)
- **Controllers:** `controller/` (interactie tussen view en repositories)
- **Models:** `model/` (domeinmodellen zoals `Article`, `UserPreferences`, `AppLanguage`)
- **Data laag:**
  - `data/network/` voor WordPress REST API DTO's/interface
  - `data/repository/` voor app-instellingen en artikeldata
- **Utility:** `util/NetworkFactory` voor Retrofit op basis van taal

## Waarom REST API als basis

Voor de app is de WordPress REST API als primaire route gekozen omdat dit:
1. een native, moderne UI mogelijk maakt;
2. betere controle geeft over filtering, caching en notificaties;
3. beter aansluit op meertaligheid (`/` en `/en` context) in eigen app-flow.

Een WebView-fallback kan later per scherm toegevoegd worden (bijv. voor **Tip de redactie** formulierpagina).

## Ingebouwde feature-opzet

- Backward compatibility met moderne look:
  - `minSdk = 24`
  - Material 3 thema met klassieke View-systemen.
- Taalkeuze met persistentie:
  - opgeslagen via `SharedPreferences` in `SettingsRepository`.
- Eigen app-menu met placeholders voor:
  - Taal
  - Gebruiker
  - Pushmeldingen
  - Tip de redactie

## Volgende iteratie (niet in deze structuur-opzet)

1. Detailpagina voor artikel (in-app of Custom Tabs).
2. Echte push-instellingen + Firebase Cloud Messaging.
3. Formulierpagina "Tip de redactie" in WebView/Custom Tabs.
4. Robuuste HTML parsing voor titelvelden vanuit WordPress `rendered` content.
5. Unit tests en UI tests.
