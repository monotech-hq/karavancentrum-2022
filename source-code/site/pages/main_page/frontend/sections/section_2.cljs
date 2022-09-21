

(ns site.pages.main-page.frontend.sections.section-2
  (:require
    [x.app-core.api :as a]))

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
  [:button {:key id
            :data-selected true
            :class "filters--icon"
            :on-click #(a/dispatch [:db/set-item! [::filters]])}
   [:img {:src icon}]])

(defn filters []
  [:div#filters--container
    (map (fn [[id conf]]
           [vehicle-type-button id conf])
         filter-config)])

(defn vehicle-name [name]
  [:p.name name])

(defn vehicle [{:vehicle/keys [thumbnail name] :as props}]
  [:div.vehicles--card {:style {:background-image (str "url("thumbnail")")}}
    [vehicle-name name]])

(defn vehicles []
  (let [data @(a/subscribe [:db/get-item [:vehicles]])]
    [:div#vehicles--container
      (map vehicle
           data)]))


(defn section-2 []
  [:section#section-2
   [section-title]
   [filters]
   [vehicles]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view []
  [section-2])
