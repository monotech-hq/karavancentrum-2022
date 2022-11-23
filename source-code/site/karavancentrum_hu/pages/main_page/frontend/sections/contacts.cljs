
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.contacts
    (:require [re-frame.api                 :as r]
              [site.components.frontend.api :as components]
              [uri.api                      :as uri]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn social-media-link
  [link icon-class label]
  [:a.kc-social-media-link {:href (uri/valid-uri link) :target "_blank" :title label}
                           [:i {:class icon-class}]
                           [:span (uri/uri->trimmed-uri (str "https://www." link))]])

(defn facebook-links
  []
  (let [facebook-links @(r/subscribe [:x.db/get-item [:site :website-contacts :facebook-links]])]
       (letfn [(f [links link] (conj links [social-media-link link [:fab :fa-facebook] "Tovább a Facebook-ra"]))]
              (reduce f [:<>] facebook-links))))

(defn instagram-links
  []
  (let [instagram-links @(r/subscribe [:x.db/get-item [:site :website-contacts :instagram-links]])]
       (letfn [(f [links link] (conj links [social-media-link link [:fab :fa-instagram] "Tovább a Instagram-ra"]))]
              (reduce f [:<>] instagram-links))))

(defn youtube-links
  []
  (let [youtube-links @(r/subscribe [:x.db/get-item [:site :website-contacts :youtube-links]])]
       (letfn [(f [links link] (conj links [social-media-link link [:fab :fa-youtube] "Tovább a Youtube-ra"]))]
              (reduce f [:<>] youtube-links))))

(defn social-media-links
  []
  [:<> [facebook-links]
       [instagram-links]
       [youtube-links]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn contacts
  []
  [:<> [:div#kc-contacts [:p.kc-section-title "Kapcsolat"]
                         [components/contacts ::contacts {}]
                         [:div#kc-social-media-links [social-media-links]]]
       [:div#kc-contacts--background]])

(defn view
  []
  [:section {:id :contacts}
            [contacts]])
