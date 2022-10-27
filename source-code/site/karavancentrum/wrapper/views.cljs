
(ns site.karavancentrum.wrapper.views
    (:require [mid-fruits.uri                     :as uri]
              [re-frame.api                       :as r]
              [reagent.api                        :as reagent]
              [site.karavancentrum.modules.api    :as modules]
              [site.karavancentrum.components.api :as components]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn company-name-and-slogan
  []
  (let [company-name   @(r/subscribe [:db/get-item [:site :config :company-name]])
        company-slogan @(r/subscribe [:db/get-item [:site :config :company-slogan]])]
       [:a {:href "/" :style {:text-decoration "none"}}
           [:div#kc-navbar--company-name-and-slogan [:div#kc-navbar--company-name   company-name]
                                                    [:div#kc-navbar--company-slogan company-slogan]]]))

(defn navbar-item
  [{:keys [href] :as config} label]
  [:a.kc-link.kc-effect--underline {:on-mouse-up #(-> % .-target .blur)
                                    :style {"--underline-color" "black"}
                                    :href href}
                                   label])

(defn navbar
  []
  (let [webshop-link @(r/subscribe [:db/get-item [:site :content :webshop-link]])
        webshop-link  (uri/valid-uri webshop-link)]
       [modules/navbar {:threshold 800 :align-x :right :logo [company-name-and-slogan]}
                       [navbar-item {:href "/berbeadas"}                 "Bérbeadás"]
                       [navbar-item {:href "/ertekesites"}               "Értékesítés"]
                       [navbar-item {:href webshop-link :target "_blank"} "Webáruház"]
                       [navbar-item {:href "/kapcsolat"}                 "Kapcsolat"]]))

(defn header
  []
  [navbar])

(defn site-wrapper
  [ui-structure]
  [:div#kc [header]
           [ui-structure]])

(defn view
  [ui-structure]
  [site-wrapper ui-structure])
