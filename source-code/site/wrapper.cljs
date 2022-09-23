
(ns site.wrapper
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-components.api :as components]

   [site.utils :as site.utils]
   [site.modules.api :as site.modules]
   [site.components.api :as site.components]))


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
  [site.modules/navbar {:threshold 800 :align-x :right :max-width 1200
                        :logo [logo]}
   [navbar-item {:href ""} "Bérbeadás"]
   [navbar-item {:href ""} "Értékesítés"]
   [navbar-item {:href ""} "Webáruház"]
   [navbar-item {:href "" :on-click #(site.utils/scroll-into "contacts")} "Kapcsolat"]])

(defn header []
  [navbar])

(defn footer []
  [site.modules/footer
   [:div [site.components/link {:prefix "mailto:"} "iroda@karavancentrum.hu"]]
   [:div [site.components/link {:href "hali"} "karavancentrum.hu"]]])

(defn site-wrapper [ui-structure]
  [:div#karavancentrum
   [header]
   [ui-structure]])
   ; [footer]])

(defn view [ui-structure]
  [site-wrapper
   ui-structure])

;; ---- Components ----
;; -----------------------------------------------------------------------------
