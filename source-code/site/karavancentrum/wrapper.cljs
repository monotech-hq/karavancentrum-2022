
(ns site.karavancentrum.wrapper
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-components.api :as components]

   [utils.api :as utils]
   [site.karavancentrum.modules.api :as site.karavancentrum.modules.frontend]
   [site.karavancentrum.components.api :as site.karavancentrum.components.frontend]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn logo []
  [:a {:href "/" :style {:text-decoration "none"}}
    [:div#logo-and-company-name
     [:div#company-name-and-slogan
      [:div#company-name "Karaván Centrum"]
      [:div#company-slogan "Lakóautók és kempingcikkek"]]]])

(defn navbar-item [config label]
  [:a.link.effect--underline (merge {:style {"--underline-color" "black"}}
                                    config)
   label])

(defn navbar []
  [site.karavancentrum.modules.frontend/navbar {:threshold 800 :align-x :right :max-width 1200
                                                :logo [logo]}
   [navbar-item {:href ""} "Bérbeadás"]
   [navbar-item {:href ""} "Értékesítés"]
   [navbar-item {:href ""} "Magunkról"]
   [navbar-item {:href ""} "Webáruház"]
   [navbar-item {:href "/" :on-click #(utils/scroll-into "contacts" {:behavior "smooth"
                                                                     :block    "start"
                                                                     :inline   "start"})}
    "Kapcsolat"]])

(defn header []
  [navbar])

(defn site-wrapper [ui-structure]
  [:div#karavancentrum
   [header]
   [ui-structure]])

(defn view [ui-structure]
  [site-wrapper ui-structure])

;; ---- Components ----
;; -----------------------------------------------------------------------------
