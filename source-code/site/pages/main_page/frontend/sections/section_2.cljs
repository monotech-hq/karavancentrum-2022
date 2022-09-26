

(ns site.pages.main-page.frontend.sections.section-2
  (:require
    [x.app-core.api :as a]
    [reagent.api :refer [lifecycles]]
    [site.components.api :as site.components]))

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
  (let [checked @(a/subscribe [:main-page.filters/contains? [:main-page.filters] id])
        disabled? @(a/subscribe [:main-page.filters/disabled? id])]
    [:div.filters--icon
     [:input.filters--icon-input {:type "checkbox" :id id :name id
                                  :checked  checked
                                  :disabled disabled?
                                  :on-change #(a/dispatch [:main-page.filters/select [:main-page.filters] id])}]
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
   [site.components/card {:name name
                          :src  thumbnail}]])

(defn vehicles []
  (let [data @(a/subscribe [:site/vehicles])]
    [:div#vehicles--container
       (map vehicle data)]))


(defn section-2 []
  (lifecycles
    {:component-did-mount (fn []
                            (a/dispatch [:main-page.filters/init!]))
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
