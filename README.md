# Erasmus Magazine Android (MVC)

Android nieuwsapp voor Erasmus Magazine met MVC-structuur, tweetaligheid en een volledig werkend instellingenmenu.

## Architectuur

- **Model:** `model/` (`Article`, `UserPreferences`, `AppLanguage`)
- **View:** `view/` (`MainActivity`, adapters en settings-activiteiten)
- **Controller:** `controller/` (`MainController`, `SettingsController`)
- **Data:**
  - `data/network/` (WordPress API en DTO's)
  - `data/repository/` (artikelen + persistente instellingen)

## Features (geïmplementeerd)

1. **Backward compatible & modern UI**
   - `minSdk = 24`
   - Material 3, toolbar, swipe-to-refresh, cards

2. **Nieuws ophalen via WordPress REST API**
   - Native rendering van artikel-lijst
   - Openen van artikel in Custom Tabs

3. **Tweetaligheid**
   - Nederlands en Engels (`/en`) via taalafhankelijke API URL
   - Persistente taalinstelling via `SharedPreferences`

4. **Eigen app-menu (werkend)**
   - **Taal:** taalkeuze-dialoog
   - **Gebruiker:** apart scherm om gebruikersnaam op te slaan
   - **Pushmeldingen:** apart scherm met aan/uit switch
   - **Tip de redactie:** formulierpagina in ingebouwde WebView-activiteit

## Opmerking

Dit project gebruikt bewust klassieke Android Views + MVC (geen Compose/MVVM), omdat dat expliciet gevraagd is.
