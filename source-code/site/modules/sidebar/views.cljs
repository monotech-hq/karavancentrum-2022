
(ns site.modules.sidebar.views
  (:require
   [x.app-core.api :as a :refer [dispatch]]
   [x.app-elements.api :as elements]
   [x.app-components.api :as components]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn sidebar-in? [db id]
  (get-in db [::sidebar id :in] false))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-view-props [db [_ {:keys [id]}]]
    {:id id
     :in (get-in db [::sidebar id :in] false)})

(a/reg-sub ::get-view-props get-view-props)

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn close-button [id]
  [:div.sidebar--close-button
   [:button {:on-click #(dispatch [:close-sidebar! id])}
    [elements/icon {:icon :close}]]])

(defn sidebar-cover [{:keys [id in]}]
  [:div.sidebar--cover {:on-click     #(dispatch [:close-sidebar! id])
                        :data-visible in}])


(defn sidebar [{:keys [id direction]
                :or {direction "right"}}
               comp {:keys [in] :as view-props}]
  [:<>
   [sidebar-cover view-props]
   [:div.sidebar--container {:data-open in :data-direction direction}
    ;[open-button view-props]
    [:div.sidebar--content
     [close-button id]
     comp]]])

(defn view [id comp]
  [components/stated
   id
   {:component  [sidebar id comp]
    :subscriber [::get-view-props id]}])

;; ---- Components ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Effect events ----

(a/reg-event-fx
  :open-sidebar!
  (fn [{:keys [db]} [_ id]]
    {:dispatch [:db/set-item! [::sidebar id :in] true]}))

(a/reg-event-fx
  :close-sidebar!
  (fn [{:keys [db]} [_ id]]
    {:dispatch [:db/set-item! [::sidebar id :in] false]}))

;; ---- Effect events ----
;; -----------------------------------------------------------------------------
