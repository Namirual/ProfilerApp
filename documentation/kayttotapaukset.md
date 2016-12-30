# Sovelluksen käyttötapauksia
Sovelluksen pääasialliset käyttötapaukset ovat:
- Käyttäjä rekisteröityy palveluun.
- Käyttäjä lisää profiilin, johon lisää kuvan itsestään, sekä täyttää haluamansa tiedot. - Käyttäjä arvioi muiden käyttäjien kuvia.
- Käyttäjä vertaa omia vastauksiaan muiden käyttäjien keskimääräisiin vastauksiin.
- Käyttäjä tarkistaa oman profiilinsa profiilituloksen.
- Käyttäjä lisää tililleen profiilin.
- Käyttäjä poistaa tililtään profiilin.
Lisäksi käyttäjä voi poistaa käyttäjätilinsä kokonaan.


Adminilla on kaikki samat ominaisuudet kuin käyttäjälläkin. Adminilla on lisäksi omia toimintojaan, joihin pääsee käsiksi Admin-sivulta ja Create Question-sivulta, joihin pääsee kaikkien sivujen yläpalkista. Adminin omat käyttötapaukset ovat:
- Admin lisää kysymyksen listaan, joista profiilia luodessa kysymykset valitaan.
- kysymyksellä täytyy olla vähintään kaksi vastausvaihtoehtoa. - Admin näkee kaikki profiilit Admin-sivulta.
- Admin näkee kaikki käyttäjät ja käyttäjien oikeudet Admin-sivulta.
- Admin poistaa minkä tahansa profiilin Admin-sivulta.
- Admin poistaa minkä tahansa accountin Admin-sivulta.

# Sovelluksen jatkokehitys
Sovelluksen tietokantarakennetta voisi päivittää tehokkaammaksi ja suoraviivaisemmaksi, jotta esim. käyttäjän poistaminen on helpompaa.
- Sovellukseen lisättävät kuvat voisi skaalata ja leikata kaikki samanmuotoisiksi, jotta sovelluksen ulkoasu olisi yhtenäisempi.
- Profiilia luodessa voisi käyttäjälle esittää esikatselun ennen profiilin tallentamista, jotta käyttäjä voi esim. tarkistaa, ettei yllä mainittu kuvanmuokkaus ole pilannut kuvaa.
- Tällä hetkellä profiilien määrää ei ole rajoitettu, mutta tulevaisuudessa käyttäjän profiilien määrän voisi rajoittaa ainakin kerralla aktiivisena olevien profiilien osalta. Tähän on jo tietokannassa profiileille olemassa “active” -boolean-muuttuja, mutta siihen liittyvää toiminnallisuutta ei ole vielä toteutettu.
- Sovelluksen käyttöliittymä voisi jatkossa antaa enemmän palautetta virheellisestä syötteestä. Tällä hetkellä tämä on toteutettu lähinnä vain adminin createquestion-sivulla, sekä login ja signup -sivuilla.
- Sovelluksen toimintaa voisi tehostaa erityisesti kuvien lataamisen osalta käyttämällä välimuistia.
- Todellisen käytön kannalta olisi hyödyllistä, että admin voisi säätää toiminnallisuuksien parametrejä, kuten sitä, kuinka monta kysymystä profiilissa täytyy olla, ja millä tavoin käyttäjille tarjotaan tietoa vastauksistaan esim. niin, että vastausprosentin saa vasta kun on arvioinut useita profiileja.
