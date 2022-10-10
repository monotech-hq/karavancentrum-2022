

(ns site.karavancentrum.pages.main-page.frontend.sections.renting
    (:require [re-frame.api                       :as r]
              [reagent.api                        :refer [lifecycles]]
              [site.karavancentrum.components.api :as site.karavancentrum.components.frontend]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def filter-config {:alcove-rv          {:icon "/site/icons/alcove-rv.png"}
                    :caravan            {:icon "/site/icons/caravan.png"}
                    :semi-integrated-rv {:icon "/site/icons/semi-integrated-rv.png"}
                    :trailer            {:icon "/site/icons/trailer.png"}
                    :van-rv             {:icon "/site/icons/van-rv.png"}})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn section-title
  []
  [:p.kc-section-title "Bérelhető járműveink"])

(defn vehicle-type-button
  [id {:keys [icon]}]
  (let [checked   @(r/subscribe [:main-page.filters/contains? [:main-page.filters] id])
        disabled? @(r/subscribe [:main-page.filters/disabled? id])]
       [:div.filters--icon
        [:input.filters--icon-input {:type "checkbox" :id id :name id :checked checked :disabled disabled?
                                     :on-change #(r/dispatch [:main-page.filters/select [:main-page.filters] id])}]
        [:label.filters--icon-img {:for id}
                                  [:img {:src icon}]]]))

(defn filters
  []
  [:div#filters--container (letfn [(f [[id conf]] ^{:key id}[vehicle-type-button id conf])]
                                  (map f filter-config))])

(defn vehicle-name
  [name]
  [:p.link.effect--underline.name name])

(defn vehicle
  [{:vehicle/keys [id link-name thumbnail name] :as props}]
  [:a {:style {:text-decoration "none" :margin "45px"}
       :key id :href (str "/berelheto-jarmuveink/" link-name)}
      [site.karavancentrum.components.frontend/card {:name name :src thumbnail}]])

(defn vehicles
  []
  (let [data @(r/subscribe [:site/vehicles])]
       [:div#vehicles--container (map vehicle data)]))

(defn renting
  []
  (lifecycles
    {:component-did-mount (fn [] (r/dispatch [:main-page.filters/init!]))
     :reagent-render      (fn [] [:section#renting [section-title]
                                                   [:a#kc-rent-informations-button "Bérlési feltételek"]
                                                   [filters]
                                                   [vehicles]])}))

(defn view
  []
  [renting])
