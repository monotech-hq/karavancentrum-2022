
(ns app.components.frontend.user-avatar.prototypes
    (:require [mid-fruits.candy :refer [param]]
              [re-frame.api     :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn avatar-props-prototype
  ; @param (map) avatar-props
  ;  {:first-name (string)(opt)
  ;   :last-name (string)(opt)}
  ;
  ; @return (map)
  ;  {:colors (strings in vector)
  ;   :initials (string)}
  [{:keys [first-name last-name] :as avatar-props}]
  (merge {:colors    ["var( --color-muted )"]
          :initials @(r/subscribe [:locales/get-ordered-initials first-name last-name])}
         (param avatar-props)))
