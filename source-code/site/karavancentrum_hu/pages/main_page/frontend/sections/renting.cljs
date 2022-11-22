

(ns site.karavancentrum-hu.pages.main-page.frontend.sections.renting
    (:require [elements.api                          :as elements]
              [plugins.reagent.api                   :refer [lifecycles]]
              [re-frame.api                          :as r]
              [site.karavancentrum-hu.components.api :as site.components]
              [vector.api                            :as vector]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(def filter-config {:alcove-rv          {:icon "/site/icons/filter-icons/alcove-rv-light.png"
                                         :tooltip "Alkóvos lakóautók"}
                    :semi-integrated-rv {:icon "/site/icons/filter-icons/semi-integrated-rv-light.png"
                                         :tooltip "Részintegrált lakóautók"}
                    :van-rv             {:icon "/site/icons/filter-icons/van-rv-light.png"
                                         :tooltip "Kempingbuszok"}
                    :caravan            {:icon "/site/icons/filter-icons/caravan-light.png"
                                         :tooltip "Lakókocsik"}
                    :trailer            {:icon "/site/icons/filter-icons/trailer-light.png"
                                         :tooltip "Utánfutók"}})

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn section-title
  []
  [:p.kc-section-title "Bérelhető járműveink"])

(defn vehicle-type-button
  [id {:keys [icon tooltip]}]
  (let [checked   @(r/subscribe [:main-page.filters/contains? [:main-page.filters] id])
        disabled? @(r/subscribe [:main-page.filters/disabled? id])]
       [:div.kc-filters--icon {:data-disabled (boolean disabled?)}
                              [:input {:type "checkbox" :id id :name id :checked checked :disabled disabled? :class :kc-filters--icon-input
                                       :on-change #(r/dispatch [:main-page.filters/select [:main-page.filters] id])}]
                              [:label {:for id :class :kc-filters--icon-img
                                       :title tooltip}
                                      [:img {:src icon}]]]))

(defn filters
  []
  [:div#kc-filters--container [:div "Bérelhető járműtípusok"]
                              [:div#kc-filters
                               (letfn [(f [[id conf]] ^{:key id} [vehicle-type-button id conf])]
                                      (map f filter-config))]])

(defn vehicle-name
  [name]
  [:p.kc-link.mt-effect--underline.name name])

(defn vehicle-card
  [{:vehicle/keys [order id link-name] :as vehicle}]
  [:a {:class :kc-vehicle-card-wrapper :key id
       :href  (str "/berelheto-jarmuveink/" link-name)
       :style {:text-decoration "none"}}
      [site.components/vehicle-card vehicle]])

(defn vehicle-list
  []
  (if @(r/subscribe [:main-page.filters/no-filter-enabled?])
       [elements/text {:color   :muted
                       :content "Bérelhető járműveink megtekintéséhez válasszon járműkategóriát!"
                       :horizontal-align :center
                       :indent  {:horizontal :xxl :vertical :s}}]
       (let [vehicles @(r/subscribe [:site/vehicles])]
            (letfn [(f [vehicles vehicle] (conj vehicles [vehicle-card vehicle]))]
                   [:div#kc-vehicles--container (reduce f [:<>] (vector/sort-items-by vehicles :vehicle/order))]))))

(defn renting
  []
  (lifecycles
    {:component-did-mount (fn [] (r/dispatch [:main-page.filters/init!]))
     :reagent-render      (fn [] [:div#kc-renting [section-title]
                                                  [filters]
                                                  [vehicle-list]
                                                  [:a {:class :kc-content-button :href "/berlesi-feltetelek"} "Bérlési feltételek"]])}))

(defn view
  []
  [:section {:id :renting}
            [renting]])
