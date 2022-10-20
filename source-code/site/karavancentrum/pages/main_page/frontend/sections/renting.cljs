

(ns site.karavancentrum.pages.main-page.frontend.sections.renting
    (:require [re-frame.api                       :as r]
              [reagent.api                        :refer [lifecycles]]
              [site.karavancentrum.components.api :as site.components]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def filter-config {:alcove-rv          {:icon "/site/icons/filter-icons/alcove-rv-light.png"}
                    :semi-integrated-rv {:icon "/site/icons/filter-icons/semi-integrated-rv-light.png"}
                    :van-rv             {:icon "/site/icons/filter-icons/van-rv-light.png"}
                    :caravan            {:icon "/site/icons/filter-icons/caravan-light.png"}
                    :trailer            {:icon "/site/icons/filter-icons/trailer-light.png"}})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn section-title
  []
  [:p.kc-section-title "Bérelhető járműveink"])

(defn vehicle-type-button
  [id {:keys [icon]}]
  (let [checked   @(r/subscribe [:main-page.filters/contains? [:main-page.filters] id])
        disabled? @(r/subscribe [:main-page.filters/disabled? id])]
       [:div.kc-filters--icon [:input {:type "checkbox" :id id :name id :checked checked :disabled disabled? :class :kc-filters--icon-input
                                       :on-change #(r/dispatch [:main-page.filters/select [:main-page.filters] id])}]
                              [:label {:for id :class :kc-filters--icon-img}
                                      [:img {:src icon}]]]))

(defn filters
  []
  [:div#kc-filters--container (letfn [(f [[id conf]] ^{:key id}[vehicle-type-button id conf])]
                                  (map f filter-config))])

(defn vehicle-name
  [name]
  [:p.kc-link.kc-effect--underline.name name])

(defn vehicle
  [{:vehicle/keys [id link-name] :as vehicle}]
  [:a {:style {:text-decoration "none"}
       :key id :href (str "/berelheto-jarmuveink/" link-name)}
      [site.components/vehicle-card vehicle]])

(defn vehicles
  []
  (let [vehicles @(r/subscribe [:site/vehicles])]
       [:div#kc-vehicles--container (map vehicle vehicles)]))

(defn renting
  []
  (lifecycles
    {:component-did-mount (fn [] (r/dispatch [:main-page.filters/init!]))
     :reagent-render      (fn [] [:section#kc-renting [section-title]
                                                      [:a {:class :kc-content-button :href "/berlesi-feltetelek"}
                                                          "Bérlési feltételek"]
                                                      [filters]
                                                      [vehicles]])}))

(defn view
  []
  [renting])
