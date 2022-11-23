
(ns site.components.frontend.impressum.views
    (:require [elements.api     :as elements]
              [random.api       :as random]
              [re-frame.api     :as r]
              [x.dictionary.api :as x.dictionary]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn impressum
  ; @param (keyword) component-id
  ; @param (map) component-props
  [component-id component-props]
  (let [server-year       @(r/subscribe [:x.core/get-server-year])
        website-impressum @(r/subscribe [:x.db/get-item [:site :website-impressum]])]
       [:div {:id :mt-impressum}
             (if-let [company-name (:company-name website-impressum)]
                     [:div {:id :mt-impressum--company-name}
                           (if-let [company-est-year (:company-est-year website-impressum)]
                                   (let [current-year (:current-year website-impressum)]
                                        [:div {:id :mt-impressum--company-est-year}
                                              (str company-est-year"-"server-year)]))
                           (str company-name)])
             (if-let [company-reg-office (:company-reg-office website-impressum)]
                     [:div {:id :mt-impressum--company-reg-office}
                           (x.dictionary/looked-up :company-reg-office)
                           (str ": " company-reg-office)])
             (if-let [company-reg-number (:company-reg-number website-impressum)]
                     [:div {:id :mt-impressum--company-reg-number}
                           (x.dictionary/looked-up :company-reg-number)
                           (str ": " company-reg-number)])]))

(defn component
  ; @param (keyword)(opt) component-id
  ; @param (map) component-props
  ;  {}
  ;
  ; @usage
  ;  [impressum {...}]
  ;
  ; @usage
  ;  [impressum :my-impressum {...}]
  ([component-props]
   [component (random/generate-keyword) component-props])

  ([component-id component-props]
   [impressum component-id component-props]))
