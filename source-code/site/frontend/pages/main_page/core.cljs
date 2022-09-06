
(ns site.frontend.pages.main-page.core
  (:require
    [x.app-core.api :as a]))

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-view-props [db _]
  {})

(a/reg-sub ::get-view-props get-view-props)

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn main-page [{:keys [categories]}]
  [:main#main-page--content])

(defn view [_]
  (let [view-props @(a/subscribe [::get-view-props])]
    [main-page view-props]))

;; ---- Components ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Lifecycles ----

(a/reg-event-fx
 :main-page/render!
 [:ui/render-surface! :main-page {:content #'view}])

(a/reg-event-fx
 :main-page/load!
 {:dispatch-n [[:ui/set-title!]
               [:get-all-category!]
               [:main-page/render!]]})

;; ---- Lifecycles ----
;; -----------------------------------------------------------------------------
