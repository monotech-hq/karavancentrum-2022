
(ns app.contents.frontend.views
  (:require
    [x.app-core.api       :as a]
    [x.app-elements.api   :as elements]

    [forms.api         :as forms]
    [layouts.surface-a.api     :as surface-a]

    [app.common.frontend.api :as common]))

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----



;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn label [surface-id]
  [common/surface-label surface-id {:label :contents}])

(defn contents [surface-id]
  [:div
   [label surface-id]])

(defn view [surface-id]
  [surface-a/layout surface-id {:content [contents surface-id]}])

;; ---- Components ----
;; -----------------------------------------------------------------------------
