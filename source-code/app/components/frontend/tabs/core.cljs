
(ns app.components.frontend.tabs.core
  (:require
    [reagent.api          :refer [lifecycles]]
    [x.app-core.api       :as a]
    [x.app-elements.api   :as elements]))


;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn vec->map [data]
  (apply hash-map data))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn- menu-item [{:keys [view-id tab-id active?]}]
  {:active?     active?
   :label       tab-id
   :on-click    [:gestures/change-view! view-id tab-id]})

(defn- menu-bar [current-tab-id view-id tabs]
  [elements/menu-bar ::menu
    {:menu-items
      (map (fn [[tab-id _]]
             (menu-item
               {:view-id view-id
                :tab-id  tab-id
                :active? (= current-tab-id tab-id)}))
           tabs)}])

(defn default-error-tab [& args]
  [:p (str "Something wrong! " args)])

(defn- tab-controller [current-tab-id tabs-data]
  (let [model (vec->map tabs-data)]
    (get model current-tab-id
      ; (if error-tab
        ; [error-tab]
        [default-error-tab current-tab-id tabs-data])))

(defn- tabs [view-id key-value-pairs]
  (let [tabs-data      (partition 2 key-value-pairs)                            ;; Tabs key value pairs where key (keyword || string) and value ($)
        current-tab-id @(a/subscribe [:gestures/get-current-view-id view-id])]
    [:div
     [menu-bar current-tab-id view-id tabs-data]
     [elements/horizontal-line {:color :highlight}]
     [elements/horizontal-separator {:size :s}]
     [tab-controller current-tab-id key-value-pairs]]))

(defn did-mount [{:keys [view-id init-tab]} args]
  (a/dispatch [:gestures/change-view!
               view-id
               (if init-tab init-tab (first args))]))

(defn view [config & args]
  ;  @param {:config   {:view-id (keyword)
  ;                     :init-tab (keyword or string)(opt)}                     ;; if not included using the first value from args
  ;          :args     [(keyword or string), ($), ..]                           ;; args = key-value-pairs
  ;
  ; @usage [components/tabs {:view-id :my-view-id}                              ;; Tab
  ;          :tab-id [tab-1]                                                    ;; tab-is used also as title for menu items so
  ;          :tab-2  [tab-2]                                                    ;; it can be string or keyword for dictionary word
  ;          "tab-3" [tab-3]]
  ;
  ; @return [reagent-component]}
  (assert (even? (count args))
          (str "\n\n COMPONENTS/TABS FAILED!\n Args must be even!
               \n component config: \n"config"
               \n"))
  (lifecycles
    {:component-did-mount #(did-mount config args)
     :reagent-render
     (fn [{:keys [view-id]} & args]
       [tabs view-id args])}))
