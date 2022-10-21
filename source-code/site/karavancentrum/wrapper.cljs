
(ns site.karavancentrum.wrapper
    (:require [mid-fruits.uri                     :as uri]
              [re-frame.api                       :as r]
              [site.karavancentrum.modules.api    :as modules]
              [site.karavancentrum.components.api :as components]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn logo
  []
  (let [company-name   @(r/subscribe [:db/get-item [:site :config :company-name]])
        company-slogan @(r/subscribe [:db/get-item [:site :config :company-slogan]])]
       [:a {:href "/" :style {:text-decoration "none"}}
           [:div#kc-company-name-and-slogan [:div#kc-company-name   company-name]
                                            [:div#kc-company-slogan company-slogan]]]))

(defn navbar-item [{:keys [scroll-target] :as config} label]
  [:a.kc-link.kc-effect--underline (merge {:style {"--underline-color" "black"}
                                           :on-mouse-up #(-> % .-target .blur)}
                                          config)
   label])

(defn navbar
  []
  (let [webshop-link @(r/subscribe [:db/get-item [:site :content :webshop-link]])
        webshop-link  (uri/valid-uri webshop-link)]
       [modules/navbar {:threshold 800 :align-x :right :logo [logo]}
                       [navbar-item {:href "/#berbeadas"}                 "Bérbeadás"]
                       [navbar-item {:href "/#ertekesites"}               "Értékesítés"]
                       [navbar-item {:href webshop-link :target "_blank"} "Webáruház"]
                       [navbar-item {:href "/#kapcsolat"}                 "Kapcsolat"]]))

(defn header []
  [navbar])

(defn site-wrapper [ui-structure]
  [:div#karavancentrum
   [header]
   [ui-structure]])

(defn view [ui-structure]
  [site-wrapper ui-structure])
