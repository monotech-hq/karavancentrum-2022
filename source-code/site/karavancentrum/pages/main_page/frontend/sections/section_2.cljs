

(ns site.karavancentrum.pages.main-page.frontend.sections.section-2
  (:require
    [re-frame.api :as r]
    [reagent.api :refer [lifecycles]]
    [site.karavancentrum.components.api :as site.karavancentrum.components.frontend]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def filter-config
  {:alcove          {:icon "/icons/alcove.png"}
   :caravan         {:icon "/icons/caravan.png"}
   :semi-integrated {:icon "/icons/semi-integrated.png"}
   :trailer         {:icon "/icons/trailer.png"}
   :van             {:icon "/icons/van.png"}})

;; ---- Configurations ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn section-title []
  [:p#title "BÉRELHETŐ JÁRMŰVEINK"])

(defn vehicle-type-button [id {:keys [icon]}]
  (let [checked @(r/subscribe [:main-page.filters/contains? [:main-page.filters] id])
        disabled? @(r/subscribe [:main-page.filters/disabled? id])]
    [:div.filters--icon
     [:input.filters--icon-input {:type "checkbox" :id id :name id
                                  :checked  checked
                                  :disabled disabled?
                                  :on-change #(r/dispatch [:main-page.filters/select [:main-page.filters] id])}]
     [:label.filters--icon-img {:for id}
      [:img {:src icon}]]]))

(defn filters []
  [:div#filters--container
    (map (fn [[id conf]]
           ^{:key id}[vehicle-type-button id conf])
         filter-config)])

(defn vehicle-name [name]
  [:p.link.effect--underline.name name])

(defn vehicle [{:vehicle/keys [id link thumbnail name] :as props}]
  [:a {:style {:text-decoration "none"}
       :key id
       :href (str "/berelheto-jarmuveink/" link)}
   [site.karavancentrum.components.frontend/card {:name name
                                                  :src  thumbnail}]])

(defn vehicles []
  (let [data @(r/subscribe [:site/vehicles])]
    [:div#vehicles--container
       (map vehicle data)]))


(defn section-2 []
  (lifecycles
    {:component-did-mount (fn []
                            (r/dispatch [:main-page.filters/init!]))
     :reagent-render
     (fn []
       [:section#section-2
        [section-title]
        [filters]
        [vehicles]])}))

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [section-2])
